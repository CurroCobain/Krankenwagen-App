/**
 * Copyright 2023 Google LLC
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

import execa from 'execa';
/**
 * spawn vd-tool process
 *
 * @param args vd-tool cli args
 * @param options execa options
 */
export declare function vdTool(args?: readonly string[], options?: execa.Options): Promise<execa.ExecaReturnValue<string>>;
declare type VdConvertOptions = {
    outDir?: string;
    width?: number;
    height?: number;
    addHeader?: boolean;
};
declare type VdConvertResult = {
    input: string;
    output: string;
    warnings?: string[];
    errors?: string[];
};
/**
 * SVG to Vector Drawable conversion
 *
 * @param input file or directory path
 * @param options VdConvertOptions
 */
export declare function vdConvert(input: string, options?: VdConvertOptions): Promise<VdConvertResult>;
export {};
//# sourceMappingURL=index.d.ts.map