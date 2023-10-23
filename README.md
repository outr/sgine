# sgine

[![CI](https://github.com/outr/sgine/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/outr/sgine/actions/workflows/ci.yml)
[![Gitter](https://badges.gitter.im/outr/sgine.svg)](https://gitter.im/outr/sgine?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://img.shields.io/maven-central/v/org.sgine/sgine-core_2.11.svg)](https://maven-badges.herokuapp.com/maven-central/org.sgine/sgine-core_2.13)

Scala Engine for OpenGL-based Desktop, Android, and iOS game and business development.

Currently wraps around libgdx (https://libgdx.badlogicgames.com/) to provide a powerful and functionally reactive Scala framework for developing applications.

**NOTE:** Use the `video` module with discretion as it depends on VLCJ (https://github.com/caprica/vlcj) currently and unless you have a commercial license, the open-source license is GPL. Additionally, the `video` module will currently only work on desktop implementations.

## TODO 2.0
- Finish Android and iOS support
- Codebase cleanup and removal of core.old

## TODO 2.1
- Support render on change support