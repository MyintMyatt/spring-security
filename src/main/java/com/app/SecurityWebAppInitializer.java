package com.app;//package com.app;
//
//import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
////auto register spring security chain
//public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
//}


import java.util.HashMap;
import java.util.Map;

 class ComputeIfAbsentExample {
    public static void main(String[] args) {
        Map<String, Integer> wordCounts = new HashMap<>();

        // Add initial values
        wordCounts.put("apple", 5);

        System.out.println("Initial Map: " + wordCounts);

        // Using computeIfAbsent() - "banana" is absent, so value is computed
        Integer bananaCount = wordCounts.computeIfAbsent("banana", key -> key.length());
        System.out.println("Value for 'banana': " + bananaCount); // Output: 6

        // Using computeIfAbsent() - "apple" is present, so value is not recomputed
        Integer appleCount = wordCounts.computeIfAbsent("apple", key -> key.length() * 2);
        System.out.println("Value for 'apple': " + appleCount); // Output: 5

        System.out.println("Updated Map: " + wordCounts);
    }
}