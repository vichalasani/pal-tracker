package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    Map<String, String> envMap;

    public EnvController(@Value("${PORT: NOT SET}") String port, @Value("${MEMORY_LIMIT: 1m}") String s2, @Value("${CF_INSTANCE_INDEX: 34}") String s3, @Value("${CF_INSTANCE_ADDR: 123 blah}") String s4) {

        envMap = new HashMap<>();
        envMap.put("PORT", port);
        envMap.put("MEMORY_LIMIT", s2);
        envMap.put("CF_INSTANCE_INDEX", s3);
        envMap.put("CF_INSTANCE_ADDR", s4);

    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return envMap;
    }
}
