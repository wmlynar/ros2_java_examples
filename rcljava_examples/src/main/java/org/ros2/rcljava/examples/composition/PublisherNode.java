/* Copyright 2017 Esteve Fernandez <esteve@apache.org>
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

package org.ros2.rcljava.examples.composition;

import java.util.concurrent.TimeUnit;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.publisher.Publisher;
import org.ros2.rcljava.timer.WallTimer;

public class PublisherNode extends BaseComposableNode {
  private Publisher<std_msgs.msg.String> publisher;

  private WallTimer timer;

  private int count;

  public PublisherNode(long context) {
    super("publisher_node", context);
    this.count = 0;
    this.publisher = node.<std_msgs.msg.String>createPublisher(std_msgs.msg.String.class, "topic");
    this.timer = node.createWallTimer(500, TimeUnit.MILLISECONDS, this::onTimer, context);
  }

  private void onTimer() {
    std_msgs.msg.String msg = new std_msgs.msg.String();
    msg.setData("Hello, world! " + this.count);
    this.count++;
    System.out.println("Publisher: [" + msg.getData() + "]");
    PublisherNode.this.publisher.publish(msg);
  }
}
