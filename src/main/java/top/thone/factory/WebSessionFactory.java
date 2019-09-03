package top.thone.factory;


public interface WebSessionFactory {
    /**
     * 通过请求的端和用户id生成session
     * @param client
     * @param id
     * @return
     */
    String add(String client, Object id);

    String add(String client, Object id, String session);

    /**
     * 通过请求的端和用户id获取session
     * @param client
     * @param id
     * @return
     */
    String getSession(Object id, String client);

    /**
     * 通过session获取用户id
     * @param session
     * @param <T>
     * @return
     */
    <T> T getId(String session);

    <T> T getId(String sessionId, String client);

    /**
     * 判断session是否存在
     * @param key
     * @return
     */
    boolean hasSession(Object key);

    /**
     * 通过用户ID和client判断session是否存在
     * @param key
     * @Param client
     * @return
     */
    boolean hasKey(Object key, String client);

    /**
     * 推出登录，删除session
     * @param session
     * @param client
     */
    void logout(String session, String client);
}
