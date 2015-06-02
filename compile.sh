#!/bin/sh
javac *.java
rmic AuctionServerImpl
rmic AuctionClientImpl
