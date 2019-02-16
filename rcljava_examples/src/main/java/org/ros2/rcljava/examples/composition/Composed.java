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

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.executors.SingleThreadedExecutor;
import org.ros2.rcljava.executors.MultiThreadedExecutor;

public class Composed {
  public static void main(final String[] args) throws InterruptedException, Exception {
    // Initialize RCL
	long contextHandle = RCLJava.rclJavaInit(args);
    SingleThreadedExecutor exec = new SingleThreadedExecutor();
    SubscriberNode subscriberNode = new SubscriberNode(contextHandle);
    PublisherNode publisherNode = new PublisherNode(contextHandle);
    exec.addNode(subscriberNode);
    exec.addNode(publisherNode);
    exec.spin();
  }
}