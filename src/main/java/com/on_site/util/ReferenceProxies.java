package com.on_site.util;

import java.lang.ref.Reference;
import java.lang.reflect.Method;

import com.google.common.base.Preconditions;
import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;

/**
 * Provides a utility function for creating reference proxies.
 *
 * @author Chris Jester-Young
 */
public enum ReferenceProxies {
    ;

    /**
     * Creates a proxy for the given reference, based on the given
     * interface type.
     *
     * <p>The proxy implements every method in the given interface type,
     * forwarding each call to the referent.
     *
     * @param typeKey the interface type of the referent, for which a
     *                a proxy is to be created
     * @param ref the reference to dereference when invoking the proxy
     * @return a proxy for the given reference
     * @throws IllegalArgumentException if the referent is no longer accessible
     *                                  when {@code createReferenceProxy} was
     *                                  called
     * @throws IllegalStateException if the referent is no longer accessible
     *                               when a proxy method is invoked
     */
    public static <T> T createReferenceProxy(Class<T> typeKey, final Reference<T> ref) {
        Preconditions.checkArgument(ref.get() != null, "Referent is no longer accessible");
        return Reflection.newProxy(typeKey, new AbstractInvocationHandler() {
            @Override
            protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
                T obj = ref.get();
                Preconditions.checkState(obj != null, "Referent is no longer accessible");
                return method.invoke(obj, args);
            }
        });
    }
}
