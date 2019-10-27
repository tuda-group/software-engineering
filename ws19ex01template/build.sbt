name := "ex01"
exercise := 1

course := "se19"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += Resolver.bintrayRepo("stg-tud", "maven")

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test",
    "junit" % "junit" % "4.12" % "test",
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")

serverUrl := "https://submission.st.informatik.tu-darmstadt.de/submit"
