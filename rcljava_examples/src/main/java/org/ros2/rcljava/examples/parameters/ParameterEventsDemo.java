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

package org.ros2.rcljava.examples.parameters;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.parameters.ParameterEventCallback;
import org.ros2.rcljava.subscription.Subscription;

public class ParameterEventsDemo {
  private static final String NODE_NAME = SetAndGetParameters.class.getSimpleName().toLowerCase();

  private static void onParameterEvent(final rcl_interfaces.msg.ParameterEvent event) {
    System.out.println("Parameter event:");
    System.out.println(" node: " + event.getNode());
    System.out.println(" new parameters:");
    for (rcl_interfaces.msg.Parameter new_parameter : event.getNewParameters()) {
      System.out.println(String.format("  %s", new_parameter.getName()));
    }
    System.out.println(" changed parameters:");
    for (rcl_interfaces.msg.Parameter changed_parameter : event.getChangedParameters()) {
      System.out.println(String.format("  %s", changed_parameter.getName()));
    }
    System.out.println(" deleted parameters:");
    for (rcl_interfaces.msg.Parameter deleted_parameter : event.getDeletedParameters()) {
      System.out.println(String.format("  %s", deleted_parameter.getName()));
    }
  }

  public static void main(final String[] args) throws InterruptedException, Exception {
    // Initialize RCL
    long context = RCLJava.rclJavaInit();

    // Let's create a new Node
    Node node = RCLJava.createNode(NODE_NAME, context);

    Subscription<rcl_interfaces.msg.ParameterEvent> sub = node.onParameterEvent(new ParameterEventCallback() {
        @Override
        public void onEvent(rcl_interfaces.msg.ParameterEvent event) {
            ParameterEventsDemo.onParameterEvent(event);
        }
    });    
    
    RCLJava.spin(node);
  }
}
