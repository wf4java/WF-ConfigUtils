/**
 * Copyright (c) 2008, SnakeYAML
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package wf.utils.yamlconfiguration.snakeyaml.emitter;


import wf.utils.yamlconfiguration.snakeyaml.error.YAMLException;

/**
 * For emitter
 */
public class EmitterException extends YAMLException {

  private static final long serialVersionUID = -8280070025452995908L;

  /**
   * Create
   *
   * @param msg - text to show
   */
  public EmitterException(String msg) {
    super(msg);
  }
}
