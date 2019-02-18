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

package org.ros2.rcljava.examples.timer;

import java.util.concurrent.TimeUnit;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.concurrent.Callback;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.subscription.Subscription;
import org.ros2.rcljava.time.ClockType;
import org.ros2.rcljava.timer.WallTimer;

public class TimerLambda extends BaseComposableNode {
  private WallTimer timer;

  public TimerLambda(long contextHandle) {
    super("minimal_timer", contextHandle);
    Callback timerCallback = () -> System.out.println("Hello, world!");
    timer = node.createWallTimer(500, TimeUnit.MILLISECONDS, ClockType.ROS_TIME, timerCallback, contextHandle);
  }

  public static void main(final String[] args) throws InterruptedException, Exception {
    // Initialize RCL
    long contextHandle = RCLJava.rclJavaInit(args);

    RCLJava.spin(new TimerLambda(contextHandle), contextHandle);
  }
}
