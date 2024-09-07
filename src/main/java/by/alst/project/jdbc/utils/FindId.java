package by.alst.project.jdbc.utils;

import java.util.List;

public class FindId {
    public static int findId(List<Integer> idList) {
        int id = 1;
        if (!idList.isEmpty()) {
            for (int i = 0; i < idList.size() - 1; i++) {
                if (idList.get(i + 1) - idList.get(i) > 1) {
                    id = idList.get(i) + 1;
                    break;
                }
                id = idList.get(i + 1) + 1;
            }
        }
        return id;
    }
}
