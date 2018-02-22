package com.your_company.ui_bindings_kotlin_sample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.nearit.ui_bindings.NearITUIBindings
import it.near.sdk.NearItManager
import kotlinx.android.synthetic.main.activity_permissions.*

/**
* @author Federico Boschini
*/

class PermissionsActivity : AppCompatActivity() {

    private val NEAR_PERMISSION_REQUEST = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        permission_bar.bindToActivity(this@PermissionsActivity, NEAR_PERMISSION_REQUEST)

        permissions.setOnClickListener {
            startActivityForResult(
                    //  Basic config: location and bluetooth required
                    //  + enable tap outside to close
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .enableTapOutsideToClose()
                            .build(),
                    NEAR_PERMISSION_REQUEST
            )
        }

        permissions_no_beacon.setOnClickListener({
            startActivityForResult(
                    //  Asks only for location
                    //  + tap outside to close enabled
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .noBeacon()
                            .enableTapOutsideToClose()
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

        permissions_beacon_nonblocking.setOnClickListener({
            startActivityForResult(
                    //  Asks for location and bluetooth
                    //  but the latter is not a blocking requirement
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .nonBlockingBeacon()
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

        permissions_invisible.setOnClickListener({
            startActivityForResult(
                    //  Invisible layout: asks for location and bluetooth via system dialogs
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .invisibleLayoutMode()
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

        invisible_no_beacon.setOnClickListener({
            startActivityForResult(
                    //  Invisible layout: asks only for location
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .invisibleLayoutMode()
                            .noBeacon()
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

        invisible_non_blocking.setOnClickListener({
            startActivityForResult(
                    //  Invisible layout: asks for location and bluetooth
                    //  but the latter is not a blocking requiremen
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .invisibleLayoutMode()
                            .nonBlockingBeacon()
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

        autostart_radar.setOnClickListener({
            startActivityForResult(
                    //  Asks for permissions (basic config) and if user grants all
                    //  NearIT-UI will automatically start the NearIT radar
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .automaticRadarStart()
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

        custom_header.setOnClickListener({
            startActivityForResult(
                    //  Basic config + custom header
                    NearITUIBindings.getInstance(this@PermissionsActivity)
                            .createPermissionRequestIntentBuilder()
                            .setHeaderResourceId(R.drawable.logo)
                            .build(),
                    NEAR_PERMISSION_REQUEST)
        })

    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        permission_bar.onActivityResult(requestCode, resultCode)
        if (requestCode == NEAR_PERMISSION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show()
                NearItManager.getInstance().startRadar()
            } else Toast.makeText(this, "Result KO", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        permission_bar.unbindFromActivity()
        super.onDestroy()
    }

}