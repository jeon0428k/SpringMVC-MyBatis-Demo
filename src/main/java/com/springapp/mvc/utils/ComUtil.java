package com.springapp.mvc.utils;

import java.io.File;
import java.io.IOException;

public class ComUtil {

    /**
     * 사용자 입력 경로가 기준 디렉터리 하위의 안전한 파일 경로인지 검증한다.
     *
     * @param baseDirPath 기준 디렉터리
     * @param userPath 사용자 입력 파일 경로 (null/빈값은 위험으로 판단)
     * @return true = 안전 / false = 위험
     */
    public static boolean isSafeFilePath(String baseDirPath, String userPath) {

        // ✅ null 또는 빈 값 → 안전하지 않음
        if (userPath == null || userPath.trim().isEmpty()) {
            return false;
        }

        try {
            File baseDir = new File(baseDirPath);
            File targetFile = new File(baseDir, userPath);

            String basePath = baseDir.getCanonicalPath();
            String targetPath = targetFile.getCanonicalPath();

            // 기준 디렉터리 하위 여부 확인
            if (!targetPath.startsWith(basePath + File.separator)) {
                return false;
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * JavaScript 문자열 영역에 출력되는 사용자 입력값을 XSS 공격으로부터 안전하게 이스케이프 처리한다.
     *
     * @param input JavaScript 문자열로 출력될 사용자 입력값
     * @return XSS 방어 처리가 완료된 안전한 문자열 (null 입력 시 빈 문자열 반환)
     */
    public static String escapeForJs(String input) {

        if (input == null) {
            return "";
        }

        StringBuilder escaped = new StringBuilder(input.length() + 16);

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            switch (ch) {
                case '"':  escaped.append("\\\""); break;
                case '\'': escaped.append("\\'"); break;
                case '\\': escaped.append("\\\\"); break;
                case '\n': escaped.append("\\n"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                case '\b': escaped.append("\\b"); break;
                case '\f': escaped.append("\\f"); break;
                case '<':  escaped.append("\\x3C"); break;
                case '>':  escaped.append("\\x3E"); break;
                case '&':  escaped.append("\\x26"); break;
                case '/':  escaped.append("\\/"); break;
                default:
                    if (ch < 32 || ch > 126) {
                        escaped.append(String.format("\\u%04x", (int) ch));
                    } else {
                        escaped.append(ch);
                    }
            }
        }

        return escaped.toString();
    }

    public static String escapeForJsOld(String input) {

        if (input == null) {
            return "";
        }

        return input
                .replace("'", "\\'")
                .replace("<", "\\x3C")
                .replace(">", "\\x3E")
                .replace("&", "\\x26")
                .replace("/", "\\/")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\"", "\\\"")
                .replace("\\", "\\\\");
//        return input
//                .replace("\\", "\\\\")
//                .replace("\"", "\\\"")
//                .replace("'", "\\'")
//                .replace("\r", "")
//                .replace("\n", "")
//                .replace("<", "\\x3C")
//                .replace(">", "\\x3E")
//                .replace("&", "\\x26")
//                .replace("/", "\\/");
    }

}
