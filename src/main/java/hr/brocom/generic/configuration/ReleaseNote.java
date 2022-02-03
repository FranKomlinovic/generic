package hr.brocom.generic.configuration;

import lombok.Data;

import java.util.List;

@Data
public class ReleaseNote {
    private String version;
    private List<String> bugfixes;
    private List<String> updates;
    private List<String> features;
}
