package com.krish.lalwani.network.server;
import com.krish.lalwani.network.common.*;
public interface RequestHandlerInterface
{
public Response process(Request request) throws Exception;
}