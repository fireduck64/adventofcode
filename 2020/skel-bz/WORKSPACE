load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

git_repository(
    name = "rules_jvm_external",
    remote = "https://github.com/bazelbuild/rules_jvm_external",
    commit = "9aec21a7eff032dfbdcf728bb608fe1a02c54124",
    shallow_since = "1577467222 -0500"
)

load("@rules_jvm_external//:defs.bzl", "maven_install")


git_repository(
  name = "duckutil",
  remote = "https://github.com/fireduck64/duckutil",
  commit = "bf1f4e4ba46a814c6e40b53de4921ebea950d84c",
  shallow_since = "1605330022 -0800",
)


maven_install(
    artifacts = [
        "com.google.protobuf:protobuf-java:3.5.1",
        "org.rocksdb:rocksdbjni:5.14.2",
        "junit:junit:4.12",
        "com.thetransactioncompany:jsonrpc2-server:1.11",
        "commons-pool:commons-pool:1.6",
        "commons-dbcp:commons-dbcp:1.4",
        "mysql:mysql-connector-java:6.0.6",
        "com.google.guava:guava:28.1-jre",
        "com.amazonaws:aws-java-sdk:1.11.765",
        "org.elasticsearch.client:elasticsearch-rest-high-level-client:7.6.2",
        "org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5",
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://maven.google.com",
    ],
    #maven_install_json = "//:maven_install.json",
)
# After changes run:
# bazel run @unpinned_maven//:pin

load("@maven//:defs.bzl", "pinned_maven_install")
pinned_maven_install()

