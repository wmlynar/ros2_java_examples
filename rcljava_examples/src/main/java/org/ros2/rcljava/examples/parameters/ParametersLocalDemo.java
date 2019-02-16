/* 
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

import java.util.Arrays;
import java.util.List;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.parameters.ParameterType;
import org.ros2.rcljava.parameters.ParameterVariant;

import rcl_interfaces.msg.ListParametersResult;
import rcl_interfaces.msg.ParameterDescriptor;
import rcl_interfaces.msg.SetParametersResult;

public class ParametersLocalDemo {
  private static final String NODE_NAME = ParametersLocalDemo.class.getSimpleName().toLowerCase();

  public static void main(String[] args) {
    // Initialize RCL
    long contextHandle = RCLJava.rclJavaInit(args);

    // Let's create a new Node
    Node node = RCLJava.createNode(NODE_NAME, contextHandle);

    // set parameters
    List<SetParametersResult> setParametersResults = node.setParameters(
        Arrays.<ParameterVariant>asList(new ParameterVariant("foo", 2L), new ParameterVariant("bar", "hello"),
            new ParameterVariant("baz", 1.45), new ParameterVariant("foobar", true)));
    for (SetParametersResult result : setParametersResults) {
      if (!result.getSuccessful()) {
        System.out.println("Failed to set parameter: " + result.getReason());
      }
    }

    // get parameters
    List<ParameterVariant> parameters = node.getParameters(Arrays.asList("foo", "baz"));
    for (ParameterVariant parameter : parameters) {
      System.out.println(parameter.getName() + " " + parameter.getTypeName() + " " + parameter.getValueAsString() + " "
          + parameter.getType().toString());
    }

    // get parameter types
    List<ParameterType> parameterTypes = node.getParameterTypes(Arrays.asList("foo", "baz"));
    for (ParameterType type : parameterTypes) {
      System.out.println("Parameter type: " + type.toString());
    }

    // list parameters
    ListParametersResult listParametersResult = node.listParameters(Arrays.asList("foo", "baz"), 10);
    for (String str : listParametersResult.getNames()) {
      System.out.println("Parameter name: " + str);
    }
    for (String str : listParametersResult.getPrefixes()) {
      System.out.println("Parameter prefix: " + str);
    }

    // describe parameters
    List<ParameterDescriptor> parameterDescriptions = node.describeParameters(Arrays.asList("foo", "baz"));
    for (ParameterDescriptor description : parameterDescriptions) {
      System.out.println("Parameter description, name: " + description.getName() + ", type: " + ParameterType.fromByte(description.getType()));
    }

    node.dispose();
    RCLJava.shutdown(contextHandle);
  }
}
