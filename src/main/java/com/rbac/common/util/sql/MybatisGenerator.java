package com.rbac.common.util.sql;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis自动生成mapper和bean<br>
 * 
 * 注意安全：重新运行会覆盖原有的代码
 * 
 * @author wlfei
 *
 */
public class MybatisGenerator {

	public static void main(String[] args) throws Exception {
		// 运行时间需要改成当天才能运行
		String today = "2021-03-29";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = sdf.parse(today);
		Date d = new Date();

		if (d.getTime() > now.getTime() + 1000 * 60 * 60 * 24) {
			System.err.println("——————未成成功运行——————");
			System.err.println("——————未成成功运行——————");
			System.err.println("本程序具有破坏作用，应该只运行一次，如果必须要再运行，需要修改today变量为今天，如:" + sdf.format(new Date()));
			return;
		}

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		InputStream is = MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(is);
		is.close();
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);

		System.out.println("生成代码成功，只能执行一次，以后执行会覆盖掉mapper,pojo,xml 等文件上做的修改");

	}
}