/*
 * 
 * Adapted for SwingJS by Bob Hanson to allow two UI sources to both contribute. In 
 * my case it was ButtonListener and JSMenuItemUI
 * 
 * Some portions of this file have been modified by Robert Hanson hansonr.at.stolaf.edu 2012-2017
 * for use in SwingJS via transpilation into JavaScript using Java2Script.
 *
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package swingjs.plaf;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ActionMapUIResource;

/**
 * An ActionMap that populates its contents as necessary. The
 * contents are populated by invoking the <code>loadActionMap</code>
 * method on the passed in Object.
 *
 * @author Scott Violet
 */
@SuppressWarnings("rawtypes")
class LazyActionMap extends ActionMapUIResource {
    /**
     * Object to invoke <code>loadActionMap</code> on. This may be
     * a Class object.
     */
    Object _loader;
    String _key; // SwingJS added to allow ButtonListener and JMenuItemUI both to contribute

    /**
     * Installs an ActionMap that will be populated by invoking the
     * <code>loadActionMap</code> method on the specified Class
     * when necessary.
     * <p>
     * This should be used if the ActionMap can be shared.
     *
     * @param c JComponent to install the ActionMap on.
     * @param loaderClass Class object that gets loadActionMap invoked
     *                    on.
     * @param defaultsKey Key to use to defaults table to check for
     *        existing map and what resulting Map will be registered on.
     */
	static void installLazyActionMap(JComponent c, Class loaderClass,
                                     String defaultsKey) {
        ActionMap map = (ActionMap)UIManager.get(defaultsKey);
        if (map == null) {
            map = new LazyActionMap(loaderClass, defaultsKey);
            UIManager.getLookAndFeelDefaults().put(defaultsKey, map);
        }
        SwingUtilities.replaceUIActionMap(c, map);
    }

    /**
     * Returns an ActionMap that will be populated by invoking the
     * <code>loadActionMap</code> method on the specified Class
     * when necessary.
     * <p>
     * This should be used if the ActionMap can be shared.
     *
     * @param c JComponent to install the ActionMap on.
     * @param loaderClass Class object that gets loadActionMap invoked
     *                    on.
     * @param defaultsKey Key to use to defaults table to check for
     *        existing map and what resulting Map will be registered on.
     */
    static ActionMap getActionMap(Class loaderClass,
                                  String defaultsKey) {
        ActionMap map = (ActionMap)UIManager.get(defaultsKey);
        if (map == null) {
            map = new LazyActionMap(loaderClass, defaultsKey);
            UIManager.getLookAndFeelDefaults().put(defaultsKey, map);
        }
        return map;
    }


    private LazyActionMap(Class loader, String key) {
        _loader = loader;
        _key = key;
    }	

    public void put(Action action) {
        put(action.getValue(Action.NAME), action);
    }

    @Override
		public void put(Object key, Action action) {
        loadIfNecessary();
        super.put(key, action);
    }

    @Override
		public Action get(Object key) {
        loadIfNecessary();
        return super.get(key);
    }

    @Override
		public void remove(Object key) {
        loadIfNecessary();
        super.remove(key);
    }

    @Override
		public void clear() {
        loadIfNecessary();
        super.clear();
    }

    @Override
		public Object[] keys() {
        loadIfNecessary();
        return super.keys();
    }

    @Override
		public int size() {
        loadIfNecessary();
        return super.size();
    }

    @Override
		public Object[] allKeys() {
        loadIfNecessary();
        return super.allKeys();
    }

    @Override
		public void setParent(ActionMap map) {
        loadIfNecessary();
        super.setParent(map);
    }

    private void loadIfNecessary() {
        if (_loader != null) {
          @SuppressWarnings("unused")
			Object loader = _loader;
        	_loader = null;
        	/**
        	 * @j2sNative
        	 * 
        	 * loader.$clazz$.loadActionMap$swingjs_plaf_LazyActionMap(this);
        	 */
        	{}
//
//            Class klass = (Class)loader;
//            try {
//                Method method = klass.getDeclaredMethod("loadActionMap",
//                                      new Class[] { LazyActionMap.class });
//                method.invoke(klass, new Object[] { this });
//            } catch (NoSuchMethodException nsme) {
//                assert false : "LazyActionMap unable to load actions " +
//                        klass;
//            } catch (IllegalAccessException iae) {
//                assert false : "LazyActionMap unable to load actions " +
//                        iae;
//            } catch (InvocationTargetException ite) {
//                assert false : "LazyActionMap unable to load actions " +
//                        ite;
//            } catch (IllegalArgumentException iae) {
//                assert false : "LazyActionMap unable to load actions " +
//                        iae;
//            }
        }
    }
}
