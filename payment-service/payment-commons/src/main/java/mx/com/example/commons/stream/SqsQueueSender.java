package mx.com.example.commons.stream;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import mx.com.example.commons.to.PaymentEventTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

public class SqsQueueSender {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    private String endPoint="https://sqs.us-east-1.amazonaws.com/262583979852/ticket_events.fifo";
    public void putMessagedToQueue(PaymentEventTO payment){
        // Message for FIFO Queue
        Map<String, Object> headers = new HashMap<>();
        // Message Group ID being set
        headers.put(SqsMessageHeaders.SQS_GROUP_ID_HEADER, "1");
        // Below is optional, since Content based de-duplication is enabled
        //headers.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, "2");
        queueMessagingTemplate.send(endPoint,
                MessageBuilder.withPayload(payment).copyHeaders(headers).build());
    }

}
