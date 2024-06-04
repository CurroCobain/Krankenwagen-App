#!/usr/bin/env node
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


// Converts SVG files to VectorDrawable XML files.
// Displays VectorDrawables.
// Usage: [-c] [-d] [-in <file or directory>] [-out <directory>] [-widthDp <size>] [-heightDp <size>] [-addHeader]
// Options:
//   -in <file or directory>:  If -c is specified, Converts the given .svg file
//                             to VectorDrawable XML, or if a directory is specified,
//                             all .svg files in the given directory. Otherwise, if -d
//                             is specified, displays the given VectorDrawable XML file
//                             or all VectorDrawables in the given directory.
//   -out <directory>          If specified, write converted files out to the given
//                             directory, which must exist. If not specified the
//                             converted files will be written to the directory
//                             containing the input files.
//   -c                        If present, SVG files are converted to VectorDrawable XML
//                             and written out.
//   -d                        Displays the given VectorDrawable(s), or if -c is
//                             specified the results of the conversion.
//   -widthDp <size>           Force the width to be <size> dp, <size> must be integer
//   -heightDp <size>          Force the height to be <size> dp, <size> must be integer
//   -addHeader                Add AOSP header to the top of the generated XML file
// Examples:
//   1) Convert SVG files from <directory> into XML files at the same directory and visualize the XML file results:
//   vd-tool -c -d -in <directory>
//   2) Convert SVG file and visualize the XML file results:
//   vd-tool -c -d -in file.svg
//   3) Display VectorDrawable's XML files from <directory>:
//   vd-tool -d -in <directory>

const { vdTool } = require('./dist')
const args = process.argv.slice(2)

vdTool(args, {
  stderr: process.stderr,
  stdout: process.stdout
}).catch(err => {
  const {exitCode} = err
  process.exit(exitCode || 1)
})
