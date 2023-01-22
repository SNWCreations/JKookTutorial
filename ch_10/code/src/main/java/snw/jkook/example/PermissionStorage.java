package snw.jkook.example;

import snw.jkook.Permission;
import snw.jkook.entity.Guild;
import snw.jkook.entity.Role;
import snw.jkook.entity.User;
import snw.jkook.util.PageIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// We shouldn't save the cache
public class PermissionStorage {
    private static final Map<String, Collection<Integer>> map = new ConcurrentHashMap<>();

    public static boolean hasPermission(User user, Guild guild) {
        Collection<Integer> roles = user.getRoles(guild);
        return get(guild).stream().anyMatch(roles::contains);
    }

    public static Collection<Integer> get(Guild guild) {
        Collection<Integer> result;
        result = map.get(guild.getId());
        if (result != null) {
            return result;
        }
        result = new ArrayList<>();
        PageIterator<Set<Role>> iter = guild.getRoles();
        while (iter.hasNext()) {
            Set<Role> next = iter.next();
            for (Role role : next) {
                if (role.isPermissionSet(Permission.MESSAGE_MANAGE)) {
                    result.add(role.getId());
                }
            }
        }
        map.put(guild.getId(), result);
        return result;
    }
    
    public static void update(Role role) {
        Collection<Integer> c = map.get(role.getGuild().getId());
        if (role.isPermissionSet(Permission.MESSAGE_MANAGE)) {
            c.add(role.getId());
        } else {
            c.remove(role.getId());
        }
    }
}