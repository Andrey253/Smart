package common;

import java.util.List;

/**
 * Менеджер ролей
 * @author DushinovSV
 * Created by house on 26.07.2018.
 */

public class RoleManager {

    /**
     * Название роли "Администратор"
     */
    public static final String ADMIN_ROLE_NAME = "ADMIN";

    /**
     * Название роли "Диспетчер"
     */
    public static final String DISPATCHER_ROLE_NAME = "DISPATCHER";

    /**
     * Название роли "Водитель"
     */
    public static final String DRIVER_ROLE_NAME = "DRIVER";

    /**
     * Метод отвечает на вопрос имеется ли в списке роль администратор
     * @param roles Список ролей
     */
    public static boolean isAdmin(List<String> roles) {
        return containsRole(roles, ADMIN_ROLE_NAME);
    }

    /**
     * Метод отвечает на вопрос имеется ли в списке роль диспетчер
     * @param roles Список ролей
     */
    public static boolean isDispatcher(List<String> roles) {
        return containsRole(roles, DISPATCHER_ROLE_NAME);
    }

    /**
     * Метод отвечает на вопрос имеется ли в списке роль водитель
     * @param roles Список ролей
     */
    public static boolean isDriver(List<String> roles) {
        return containsRole(roles, DRIVER_ROLE_NAME);
    }

    /**
     * Метод отвечает на вопрос имеется ли в списке роль водитель
     * @param roles Список ролей
     * @param needRole Нужная роль
     */
    private static boolean containsRole(List<String> roles, String needRole) {
        String needRoleLower = needRole.toLowerCase();
        boolean result = false;

        for (int indexRole = 0; indexRole < roles.size(); ++indexRole) {
            String role = roles.get(indexRole).toLowerCase();
            if (role.equals(needRoleLower)) {
                result = true;
            }
        }
        return result;
    }
}