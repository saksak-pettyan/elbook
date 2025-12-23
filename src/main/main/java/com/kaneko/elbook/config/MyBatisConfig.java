package com.kaneko.elbook.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("com.kaneko.elbook.mapper")
public class MyBatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

		// DataSource を MyBatis に渡す
		factoryBean.setDataSource(dataSource);

		// ★ ここが今回のポイントです
		factoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver()
						.getResources("classpath:/mappers/*.xml"));

		// typeAlias 用のパッケージ（domain クラス）
		factoryBean.setTypeAliasesPackage("com.kaneko.elbook.domain");

		return factoryBean.getObject();
	}
}
