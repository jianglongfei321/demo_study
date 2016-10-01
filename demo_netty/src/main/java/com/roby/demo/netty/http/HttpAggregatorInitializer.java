package com.roby.demo.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

public abstract class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
	@Override
	protected void initChannel(Channel ch) throws Exception {
		 
		  ChannelPipeline pipeline = ch.pipeline();                 
         pipeline.addLast("decoder", new HttpRequestDecoder());  
          /**usually we receive http message infragment,if we want full http message, 
         * we should bundle HttpObjectAggregator and we can get FullHttpRequest。 
        * 我们通常接收到的是一个http片段，如果要想完整接受一次请求的所有数据，我们需要绑定HttpObjectAggregator，然后我们 
        * 就可以收到一个FullHttpRequest-是一个完整的请求信息。 
       **/  
    pipeline.addLast("servercodec",new HttpServerCodec());  
    pipeline.addLast("aggegator",new HttpObjectAggregator(10224*1024*64));//定义缓冲数据量  
  //添加解压缩Handler
    pipeline.addLast("decompressor", new HttpContentDecompressor());
    pipeline.addLast(new EchoServerHandler());  
    pipeline.addLast("responseencoder",new HttpResponseEncoder());  
	}

}
