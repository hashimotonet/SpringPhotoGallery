/**
 * 
 */
package hashimotonet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import hashimotonet.entity.PhotoEntity;

/**
 * @author hashi
 *
 */
@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer>, 
											 JpaSpecificationExecutor<PhotoEntity> {
	
	/**
	 * Photo表より、Identityで示されるアカウントに紐づく画像一覧を取得する。
	 * 
	 * @param identity
	 * @return
	 */
	public List<PhotoEntity> findAllByIdentity(String identity);
	
}
