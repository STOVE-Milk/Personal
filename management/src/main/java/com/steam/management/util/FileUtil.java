package com.steam.management.util;

import com.steam.management.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@Component
public class FileUtil {
    private final String path;

    public FileUtil(@Value("${file.path.deleted-user}") String path) {
        this.path = path;
    }

    public void writeUserData(User user) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf(Locale.KOREA, "{\"id\":\"%d\",",user.getId());
        printWriter.printf(Locale.KOREA, "\"email\":\"%s\",",user.getEmail());
        printWriter.printf(Locale.KOREA, "\"password\":\"%s\",",user.getPassword());
        printWriter.printf(Locale.KOREA, "\"nickname\":\"%s\",",user.getNickname());
        printWriter.printf(Locale.KOREA, "\"role\":\"%b\",",user.getRole());
        printWriter.printf(Locale.KOREA, "\"verified\":\"%b\",",user.getVerified());
        printWriter.close();
    }
}
