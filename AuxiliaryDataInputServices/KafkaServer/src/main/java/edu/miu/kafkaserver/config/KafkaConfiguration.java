package edu.miu.kafkaserver.config;

import edu.miu.kafkaserver.domain.CpuData;
import edu.miu.kafkaserver.domain.DiskData;
import edu.miu.kafkaserver.domain.NetworkData;
import edu.miu.kafkaserver.domain.RamData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, CpuData> cpuDataConsumerFactory() {
        JsonDeserializer<CpuData> deserializer = new JsonDeserializer<>(
                CpuData.class
        );
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = consumerConfigs();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "cpu-data-consumer-group");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CpuData> cpuDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CpuData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cpuDataConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DiskData> diskDataConsumerFactory() {
        JsonDeserializer<DiskData> deserializer = new JsonDeserializer<>(
                DiskData.class
        );
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = consumerConfigs();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "disk-data-consumer-group");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DiskData> diskDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DiskData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(diskDataConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, NetworkData> networkDataConsumerFactory() {
        JsonDeserializer<NetworkData> deserializer = new JsonDeserializer<>(
                NetworkData.class
        );
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = consumerConfigs();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "network-data-consumer-group");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NetworkData> networkDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NetworkData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(networkDataConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RamData> ramDataConsumerFactory() {
        JsonDeserializer<RamData> deserializer = new JsonDeserializer<>(
                RamData.class
        );
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = consumerConfigs();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ram-data-consumer-group");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RamData> ramDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RamData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ramDataConsumerFactory());
        return factory;
    }

}
