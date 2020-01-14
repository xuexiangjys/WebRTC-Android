/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.webrtcdemo.fragment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.xuexiang.webrtcdemo.R;
import com.xuexiang.webrtcdemo.activity.WebRTCRoomActivity;
import com.xuexiang.webrtcdemo.core.BaseFragment;
import com.xuexiang.webrtcdemo.utils.XToastUtils;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xuexiang.xaop.consts.PermissionConsts.CAMERA;
import static com.xuexiang.xaop.consts.PermissionConsts.MICROPHONE;
import static com.xuexiang.xutil.app.IntentUtils.DocumentType.VIDEO;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:16
 */
@Page(name = "WebRTC演示", anim = CoreAnim.none)
public class MainFragment extends BaseFragment implements ClickUtils.OnClick2ExitListener {

    @BindView(R.id.et_service_url)
    EditText etServiceUrl;
    @BindView(R.id.et_room_name)
    EditText etRoomName;

    @Override
    protected TitleBar initTitle() {
        return super.initTitle().setLeftClickListener(view -> ClickUtils.exitBy2Click(2000, this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews() {

    }

    @SingleClick
    @OnClick(R.id.btn_join)
    public void onViewClicked(View view) {
        String serviceUrl = etServiceUrl.getText().toString();
        String roomName = etRoomName.getText().toString();
        if (StringUtils.isEmpty(serviceUrl)) {
            XToastUtils.error("服务器地址不可为空");
            return;
        }
        if (StringUtils.isEmpty(roomName)) {
            XToastUtils.error("房间号不可为空");
            return;
        }

        startConnectToWebRtc(serviceUrl, roomName);
    }

    /**
     * 连接WebRtc
     *
     * @param serviceUrl
     * @param roomName
     */
    @Permission({CAMERA, MICROPHONE})
    private void startConnectToWebRtc(String serviceUrl, String roomName) {
        Intent intent = new Intent(getContext(), WebRTCRoomActivity.class);
        intent.putExtra("ServerAddr", serviceUrl);
        intent.putExtra("RoomName", roomName);
        startActivity(intent);
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click(2000, this);
        }
        return true;
    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }

    @Override
    public void onExit() {
        XUtil.get().exitApp();
    }


}
