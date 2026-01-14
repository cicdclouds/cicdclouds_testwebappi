package com.cicdclouds;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

public class ApiTestRunner {
    public static void main(String[] args) {
        // 1. Resolve Host, Port, and Context dynamically
        String host = System.getProperty("app.host", System.getenv("APP_HOST"));
        if (host == null || host.isEmpty()) host = "localhost";

        String port = System.getProperty("app.port", System.getenv("APP_PORT"));
        if (port == null || port.isEmpty()) port = "8080";

        // This is the 'App Name'. Use "" if it's the ROOT context (no app name)
        String context = System.getProperty("app.context", System.getenv("APP_CONTEXT"));
        if (context == null) context = "copilot"; // Your current working choice

        // 2. Build the full URL
        // Logic: If context is empty, don't add extra slashes
        String baseUrl = context.isEmpty() ? 
                String.format("http://%s:%s", host, port) : 
                String.format("http://%s:%s/%s", host, port, context);
        
        // Note: I removed '/api' here because your URL was http://localhost:9090/copilot/greet
        String appUrl = baseUrl + "/greet";
        
        System.setProperty("app.url", appUrl);

        System.out.println("--- Starting API Tests ---");
        System.out.println("Target URL: " + appUrl);

        // 3. Discover and run tests
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectPackage("com.cicdclouds"))
                .build();

        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.execute(request, listener);

        // 4. Print Summary
        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out, true));

        // 5. CRITICAL: Show WHY tests failed (Assertion errors or 404s)
        if (summary.getTotalFailureCount() > 0) {
            System.out.println("\n--- ❌ FAILURE ANALYSIS ---");
            summary.getFailures().forEach(failure -> {
                System.out.println("Test: " + failure.getTestIdentifier().getDisplayName());
                System.out.println("Reason: " + failure.getException().getMessage());
                System.out.println("---------------------------");
            });
            System.exit(1);
        }

        if (summary.getTestsFoundCount() == 0) {
            System.err.println("CRITICAL ERROR: No tests were discovered!");
            System.exit(1);
        }

        System.out.println("All tests executed successfully!");
        System.exit(0);
    }
}