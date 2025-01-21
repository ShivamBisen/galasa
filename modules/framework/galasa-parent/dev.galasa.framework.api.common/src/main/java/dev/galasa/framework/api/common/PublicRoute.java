/*
 * Copyright contributors to the Galasa project
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.framework.api.common;

import javax.servlet.http.HttpServletRequest;

import dev.galasa.framework.spi.rbac.Action;

public abstract class PublicRoute extends BaseRoute {

    public PublicRoute(ResponseBuilder responseBuilder, String path) {
        super(responseBuilder, path);
    }

    @Override
    public boolean isActionPermitted(Action action, HttpServletRequest request) throws InternalServletException {
        return true;
    }
}
