/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.remoting.exchange.support.header;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.*;
import com.alibaba.dubbo.remoting.exchange.ExchangeClient;
import com.alibaba.dubbo.remoting.exchange.ExchangeHandler;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.dubbo.remoting.exchange.Exchanger;
import com.alibaba.dubbo.remoting.transport.DecodeHandler;

/**
 * DefaultMessenger
 *
 *
 */
public class HeaderExchanger implements Exchanger {

    public static final String NAME = "header";

    @Override
    public ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException {
        // 处理器链，DecodeHandler -> HeaderExchangeHandler -> DubboProtocol.requestHandler(匿名类，处理request请求)
        ChannelHandler handler1 = new DecodeHandler(new HeaderExchangeHandler(handler));
        // 获取一个建立好连接的client
        Client client = Transporters.connect(url, handler1);
        return new HeaderExchangeClient(client, true);
    }

    @Override
    public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        // 处理器链，DecodeHandler -> HeaderExchangeHandler -> DubboProtocol.requestHandler(匿名类，处理request请求)
        ChannelHandler handler1 = new DecodeHandler(new HeaderExchangeHandler(handler));
        // 绑定到端口上，等待客户端的连接
        Server server = Transporters.bind(url, handler1);
        return new HeaderExchangeServer(server);
    }

}
