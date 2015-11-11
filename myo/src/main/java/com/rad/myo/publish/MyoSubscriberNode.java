package com.rad.myo.publish;

import com.rad.myo.data.MyoData;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import std_msgs.Bool;

public class MyoSubscriberNode extends AbstractNodeMain {

    private static final String TAG = MyoSubscriberNode.class.getSimpleName();
    private final MyoData myoData;

    private Subscriber<Bool> vibrateSubscriber;

    public MyoSubscriberNode(MyoData myoData) {
        this.myoData = myoData;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("MyoSubscriberNode_" + myoData.getMyoId());
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        vibrateSubscriber = connectedNode.newSubscriber(GraphName.of("myo_" + myoData.getMyoId() + "/vibrate"), std_msgs.Bool._TYPE);
        vibrateSubscriber.addMessageListener(new MessageListener<Bool>() {
            @Override
            public void onNewMessage(Bool bool) {
                myoData.setVibrate(bool.getData());
            }
        });
    }

}
