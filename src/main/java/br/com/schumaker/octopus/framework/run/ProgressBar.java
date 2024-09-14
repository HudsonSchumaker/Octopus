package br.com.schumaker.octopus.framework.run;

public final class ProgressBar {
    private final int total;
    private final int barLength;
    private int current;
    private String progressBar;

    public ProgressBar(int total, int barLength) {
        this.total = total;
        this.barLength = barLength;
        this.current = 0;
        this.progressBar = "";
    }

    public void update(int progress, String message) {
        current += progress;
        int progressPercentage = (int) ((double) current / total * 100);
        int filledLength = (int) ((double) current / total * barLength);

        String bar = "=".repeat(Math.max(0, filledLength)) +
                " ".repeat(Math.max(0, barLength - filledLength));

        progressBar = String.format("[%s] %d%% %s", bar, progressPercentage, message);
        display();
    }

    public void complete() {
        update(total - current, "Complete");
        System.out.println();
    }

    private void display() {
        System.out.print("\r" + progressBar);
    }
}