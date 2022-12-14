package gdsc.skhu.jpa.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// audi 기능을 사용하여 DB를 관리하기 편하도록 DB에 값을 넣을 때, 항상 특정 데이터가 포함
// https://ojt90902.tistory.com/711
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTime {

    @CreatedDate
    // 최초로 등록은 되지만 수정은 불가능한 것이 보장
    @Column(updatable = false)
    protected LocalDateTime createDate; // 생성 시간

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
