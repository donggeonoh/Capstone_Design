package com.donggeon.honmaker.extension.ted;

import com.donggeon.honmaker.App;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import io.reactivex.Single;

public class PermissionUtil {

    public static Single<TedPermissionResult> observeCheckPermission(final String... permissions) {
        return TedRx2Permission.with(App.instance().getContext())
                .setPermissions(permissions)
                .request();
    }
}
