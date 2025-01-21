/*
 * Copyright contributors to the Galasa project
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.framework.api.common;

import static dev.galasa.framework.api.common.ServletErrorMessage.GAL5125_ACTION_NOT_PERMITTED;
import static dev.galasa.framework.api.common.ServletErrorMessage.GAL5126_INTERNAL_RBAC_ERROR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.galasa.framework.spi.rbac.Action;
import dev.galasa.framework.spi.rbac.CacheRBAC;
import dev.galasa.framework.spi.rbac.RBACException;
import dev.galasa.framework.spi.rbac.RBACService;

public abstract class ProtectedRoute extends BaseRoute {

    protected RBACService rbacService;
    protected Environment env;

    public ProtectedRoute(
        ResponseBuilder responseBuilder,
        String path,
        RBACService rbacService,
        Environment env
    ) {
        super(responseBuilder, path);
        this.rbacService = rbacService;
        this.env = env;
    }

    @Override
    public void validateActionPermitted(Action action, HttpServletRequest request) throws InternalServletException {
        boolean isActionPermitted = false;
        String jwt = JwtWrapper.getBearerTokenFromAuthHeader(request);
        if (jwt != null) {
            try {
                String loginId = new JwtWrapper(jwt, env).getUsername();
                CacheRBAC cache = rbacService.getUsersActionsCache();
                isActionPermitted = cache.isActionPermitted(loginId, action.getId());
            } catch (RBACException e) {
                ServletError error = new ServletError(GAL5126_INTERNAL_RBAC_ERROR);
                throw new InternalServletException(error, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        if (!isActionPermitted) {
            ServletError error = new ServletError(GAL5125_ACTION_NOT_PERMITTED);
            throw new InternalServletException(error, HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
