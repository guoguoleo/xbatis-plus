package cn.xbatis.plus.utils;


import cn.xbatis.plus.annotations.DataScope;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class DataScopeUtil {


    private static final ThreadLocal<Deque<Object>> TL = ThreadLocal.withInitial(ArrayDeque::new);

    private static final Object NULL = new Object();

    public static DataScope getDataPermission(){
        Deque<?> stack = TL.get();
        if (stack.isEmpty()) {
            return null;
        }
        Object obj = stack.getLast();
        if (obj == NULL) {
            return null;
        }
        if (obj instanceof DataScope dataScope){
            return dataScope;
        }else {
            return null;
        }
    }

    public static void setDataPermission(DataScope dataScope) {
        TL.get().addLast(Objects.requireNonNullElse(dataScope, NULL));
    }


    public static void removeDataPermission(){
        TL.get().removeLast();
    }



}
