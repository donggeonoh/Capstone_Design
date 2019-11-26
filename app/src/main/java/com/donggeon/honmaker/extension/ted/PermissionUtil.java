package com.donggeon.honmaker.extension.ted;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import io.reactivex.Single;

public class PermissionUtil {

    /**
     * TedPermission 호출
     */
    public static Single<TedPermissionResult> requestPermission(@NonNull final Context context,
                                                                @Nullable final String... permissions) {
        return TedRx2Permission.with(context)
                .setPermissions(permissions)
                .request();
    }
}
