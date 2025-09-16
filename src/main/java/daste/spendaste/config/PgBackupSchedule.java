package daste.spendaste.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class PgBackupSchedule {

    private static final String PG_DUMP_PATH = "C:\\Program Files\\PostgreSQL\\15\\bin\\pg_dump.exe";
    private static final String HOST = "localhost";
    private static final String PORT = "5432";
    private static final String DATABASE = "mydb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "secret";
    private static final String BACKUP_DIR = "D:\\db_backups";
    private static final int RETENTION_DAYS = 30;

    // Run every day at 2 AM
    @Scheduled(cron = "0 0 2 * * *")
    public void backupDatabase() throws IOException, InterruptedException {
        createBackup();
        cleanOldBackups();
    }

    private void createBackup() throws IOException, InterruptedException {
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFile = BACKUP_DIR + File.separator + "backup_" + date + ".sql";

        ProcessBuilder pb = new ProcessBuilder(
                PG_DUMP_PATH,
                "-h", HOST,
                "-p", PORT,
                "-U", USERNAME,
                "-d", DATABASE,
                "-F", "p",
                "-f", backupFile
        );

        pb.environment().put("PGPASSWORD", PASSWORD);

        pb.redirectErrorStream(true);
        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("✅ Backup created: " + backupFile);
        } else {
            System.err.println("❌ Backup failed with code " + exitCode);
        }
    }

    private void cleanOldBackups() throws IOException {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) return;

        Instant cutoff = Instant.now().minus(RETENTION_DAYS, ChronoUnit.DAYS);

        for (File file : backupDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".sql")) {
                Instant modified = Files.getLastModifiedTime(file.toPath()).toInstant();
                if (modified.isBefore(cutoff)) {
                    if (file.delete()) {
                        System.out.println("🗑️ Deleted old backup: " + file.getName());
                    }
                }
            }
        }
    }


    public static void restoreDatabase(File backupFile) throws IOException, InterruptedException {
        if (!backupFile.exists()) {
            throw new IllegalArgumentException("Backup file not found: " + backupFile.getAbsolutePath());
        }

        ProcessBuilder pb = new ProcessBuilder(
                PG_DUMP_PATH,
                "-h", HOST,
                "-p", PORT,
                "-U", USERNAME,
                "-d", DATABASE,
                "-f", backupFile.getAbsolutePath()
        );

        pb.environment().put("PGPASSWORD", PASSWORD);

        pb.redirectErrorStream(true);
        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("✅ Database restored from: " + backupFile.getName());
        } else {
            System.err.println("❌ Restore failed with code " + exitCode);
        }
    }

    public void backupDb() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "docker", "exec", "-t", "my_postgres_container",
                "pg_dump", "-U", "myuser", "-d", "mydb",
                "-F", "c", "-f", "/backups/mydb.dump"
        );
        Process p = pb.inheritIO().start();
        p.waitFor();

        // copy to host
        new ProcessBuilder("docker", "cp",
                "my_postgres_container:/backups/mydb.dump", "./mydb.dump")
                .inheritIO().start().waitFor();
    }

    public void restoreDb() throws IOException, InterruptedException {
        // copy to container
        new ProcessBuilder("docker", "cp", "./mydb.dump",
                "my_postgres_container:/backups/mydb.dump")
                .inheritIO().start().waitFor();

        // restore inside container
        new ProcessBuilder(
                "docker", "exec", "-t", "my_postgres_container",
                "pg_restore", "-U", "myuser", "-d", "mydb",
                "--clean", "--if-exists", "/backups/mydb.dump"
        ).inheritIO().start().waitFor();
    }

}
