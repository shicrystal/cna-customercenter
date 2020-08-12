package example1;

import example1.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerCenterViewHandler {


    @Autowired
    private CustomerCenterRepository customerCenterRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                CustomerCenter customerCenter = new CustomerCenter();
                // view 객체에 이벤트의 Value 를 set 함
                customerCenter.setOrderId(ordered.getId());
                customerCenter.setProductId(ordered.getProductId());
                customerCenter.setQty(ordered.getQty());
                // view 레파지 토리에 save
                customerCenterRepository.save(customerCenter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenShiped_then_UPDATE_1(@Payload Shiped shiped) {
        try {
            if (shiped.isMe()) {
                // view 객체 조회
                List<CustomerCenter> customerCenterList = customerCenterRepository.findByOrderId(shiped.getOrderId());
                for(CustomerCenter customerCenter : customerCenterList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerCenter.setStatus(shiped.getStatus());
                    // view 레파지 토리에 save
                    customerCenterRepository.save(customerCenter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCanceled_then_UPDATE_2(@Payload DeliveryCanceled deliveryCanceled) {
        try {
            if (deliveryCanceled.isMe()) {
                // view 객체 조회
                List<CustomerCenter> customerCenterList = customerCenterRepository.findByOrderId(deliveryCanceled.getOrderId());
                for(CustomerCenter customerCenter : customerCenterList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerCenter.setStatus(deliveryCanceled.getStatus());
                    // view 레파지 토리에 save
                    customerCenterRepository.save(customerCenter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}