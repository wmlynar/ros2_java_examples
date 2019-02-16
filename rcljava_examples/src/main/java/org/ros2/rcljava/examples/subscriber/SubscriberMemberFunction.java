/* Copyright 2016-2017 Esteve Fernandez <esteve@apache.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ros2.rcljava.examples.subscriber;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.consumers.Consumer;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.subscription.Subscription;

public class SubscriberMemberFunction extends BaseComposableNode {
  private Subscription<std_msgs.msg.String> subscription;

  public SubscriberMemberFunction(long context) {
    super("minimal_subscriber", context);
    subscription = node.<std_msgs.msg.String>createSubscription(
        std_msgs.msg.String.class, "topic", this ::topicCallback);
  }

  private void topicCallback(final std_msgs.msg.String msg) {
    System.out.println("I heard: [" + msg.getData() + "]");
  }

  public static void main(final String[] args) throws InterruptedException, Exception {
    // Initialize RCL
    long context = RCLJava.rclJavaInit(args);

    RCLJava.spin(new SubscriberMemberFunction(context));
  }
}
