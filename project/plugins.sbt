/*
 * Copyright 2016 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
*/
// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe Simple Repository" at  "typesafe-releases: https://repo.typesafe.com/typesafe/maven-releases/"
// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % Option(System.getProperty("play.version")).getOrElse("2.2.2"))

// Jacoco code coverage plugin
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

// Findbugs plugin
addSbtPlugin("de.johoop" % "findbugs4sbt" % "1.4.0")

// Copy paste detector plugin
addSbtPlugin("de.johoop" % "cpd4sbt" % "1.2.0")

// Checkstyle plugin
addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "3.1.1")

// Scalastyle plugin
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")
