# GoodWatcher &ndash; Client

<!-- PROJECT LOGO -->
<br />
<div align="center">
<a href="#">
  <img src="https://i.imgur.com/FTXABBw.png" title="source: imgur.com" style="width: 64px; height: 64px;" />
</a>

<h3 align="center">GoodWatcher &ndash; Client</h3>
</div>

## Overview

GoodWatcher is a cross-platform Java Swing desktop application that utilizes Socket programming to enable remote folder monitoring. The project uses a custom UI theme inspired by WinUI 3 Fluent Design System, follow more [here](https://github.com/vanduyquang100/swingdows).

This version serves as the **_client_** component of the application, responsible for tracking changes and communicating with the server.

## Prerequisites

**Java Runtime Environment (JRE) 8** or higher.

## Features

### Connect Using Passcodes

The server-side application generates a unique hashed connection passcode for each connection request. Clients can utilize this code to establish a connection with the server.
<a href="#"><img src="https://i.imgur.com/X19LsTP.png" title="source: imgur.com" /></a>

### Server-Side Folder Tree Viewer for Clients

The server-side application provides the capability to navigate and select specific folders on clients' desktops through the directory tree directly. This feature enables convenient folder selection and monitoring from the server side.

**_Notes:_** Each PC can only have one watched folder per server.

<a href="#"><img src="https://i.imgur.com/2gGkXRu.png" title="source: imgur.com" /></a>

### Real-Time Folder Change Monitoring

The program provides real-time updates for changes made to a client's folder, ensuring instant synchronization with the server. It automatically detects when files or subfolders are edited, created, or deleted, ensuring you stay up-to-date with your folder's activity.

<a href="#"><img src="https://i.imgur.com/VhTexRZ.png" title="source: imgur.com" /></a>

### Unlimited Number Of Clients.

The server-side program can watch changes on an unlimited number of clients simultaneously.

<a href="#"><img src="https://i.imgur.com/wb5blnJ.png" title="source: imgur.com" /></a>

### Fluent Design System Applied

The program features a unique user interface created by me, inspired by the Win UI 3 Fluent Design System. It enhances the visual appeal by redesigning various UI elements.

## License

Distributed under the MIT License. See [LICENSE](https://github.com/vanduyquang100/GoodWatcher-client/blob/master/LICENSE) for more information.
