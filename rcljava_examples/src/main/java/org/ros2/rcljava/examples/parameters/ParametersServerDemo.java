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
import java.util.concurrent.ExecutionException;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.parameters.ParameterCallback;
import org.ros2.rcljava.parameters.ParameterVariant;
import org.ros2.rcljava.parameters.client.SyncParametersClientImpl;
import org.ros2.rcljava.parameters.service.ParameterServiceImpl;

import rcl_interfaces.msg.SetParametersResult;

public class ParametersServerDemo {
  private static final String NODE_NAME = ParametersServerDemo.class.getSimpleName().toLowerCase();

  public static void main(String[] args)
      throws NoSuchFieldException, IllegalAccessException, InterruptedException, ExecutionException {
    // Initialize RCL
    long contextHandle = RCLJava.rclJavaInit(args);

    // Let's create a new Node
    Node node = RCLJava.createNode(NODE_NAME, args, true, contextHandle);

    // create parameter service and client
    ParameterServiceImpl parametersService = new ParameterServiceImpl(node);
    SyncParametersClientImpl parametersClient = new SyncParametersClientImpl(node, contextHandle);

    // add callback on parameter change
    node.setParameterChangeCallback(new ParameterCallback() {
      @Override
      public SetParametersResult onParamChange(List<ParameterVariant> parameters) {
        for (ParameterVariant parameter : parameters) {
          System.out.println("Callback: " + parameter.getName() + " " + parameter.getTypeName() + " "
              + parameter.getValueAsString() + " " + parameter.getType().toString());
        }
        SetParametersResult result = new SetParametersResult();
        result.setSuccessful(true);
        return result;
      }
    });

    // set parameters
    List<SetParametersResult> setParametersResults = parametersClient.setParameters(
        Arrays.<ParameterVariant>asList(new ParameterVariant("foo", 2L), new ParameterVariant("bar", "hello"),
            new ParameterVariant("baz", 1.45), new ParameterVariant("foobar", true)));
    for (SetParametersResult result : setParametersResults) {
      if (!result.getSuccessful()) {
        System.out.println("Failed to set parameter: " + result.getReason());
      }
    }

    // get parameters
    List<ParameterVariant> parameters = parametersClient.getParameters(Arrays.asList("foo", "baz"));
    for (ParameterVariant parameter : parameters) {
      System.out.println(parameter.getName() + " " + parameter.getTypeName() + " " + parameter.getValueAsString() + " "
          + parameter.getType().toString());
    }

    RCLJava.spin(node, contextHandle);

    node.dispose();
    RCLJava.shutdown(contextHandle);
  }
}
