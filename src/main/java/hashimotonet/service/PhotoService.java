package hashimotonet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hashimotonet.entity.PhotoEntity;
import hashimotonet.model.Photo;
import hashimotonet.repository.PhotoRepository;

@Service
@Transactional
public class PhotoService {

	private final PhotoRepository repository;
	
	public PhotoService() {
		this.repository = null;
	}
	
	@Autowired
	public PhotoService(PhotoRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Photo表にインサート実行を行う。
	 * 
	 * @param photo Modelクラス
	 * @return 挿入したデータ
	 */
	@Transactional(readOnly=false)
	public Photo create(Photo photo) {
		PhotoEntity entity = new PhotoEntity();
		
		// 引数のModelよりEntityに値をコピー。
		BeanUtils.copyProperties(photo, entity);
		
		System.out.println(entity);
		
		// DBへのINSERT実行
		PhotoEntity updatedEntity = repository.save(entity);
		
		// 処理結果をModelにコピー。
		BeanUtils.copyProperties(updatedEntity, photo);
		
		return photo;
	}
	
	/**
	 * Photo表からレコード全件を取得する。
	 * 
	 * @return Photo表の内容全件
	 */
	@Transactional(readOnly=true)
	public List<Photo> getAllPhotoList() {
		List<Photo> photos = new ArrayList<>();
		List<PhotoEntity> entityList = repository.findAll();
		entityList.forEach(entity -> {
			Photo photo = new Photo();
	        BeanUtils.copyProperties(entity, photo);
	        photos.add(photo);
	    });
	    return photos;
	}

	/**
	 * Photo表より、Identityで示されるアカウントに紐づく画像一覧を取得する。
	 * 
	 * @param identity Modelクラス
	 * @return Photo表の内容全件
	 */
	@Transactional(readOnly=true)
	public List<Photo> getAllPhotoList(Photo src) {
		List<Photo> photos = new ArrayList<>();
		List<PhotoEntity> entityList = repository.findAllByIdentity(src.getIdentity());
		entityList.forEach(entity -> {
			Photo photo = new Photo();
	        BeanUtils.copyProperties(entity, photo);
	        photos.add(photo);
	    });
	    return photos;
	}

	/**
	 * Photo表より、Identityで示されるアカウントに紐づく画像一覧を取得する。
	 * 
	 * @param identity アカウント名称
	 * @return Photo表の内容全件
	 */
	@Transactional(readOnly=true)
	public List<Photo> getAllPhotoList(String identity) {
		List<Photo> photos = new ArrayList<>();
		List<PhotoEntity> entityList = repository.findAllByIdentity(identity);
		entityList.forEach(entity -> {
			Photo photo = new Photo();
	        BeanUtils.copyProperties(entity, photo);
	        photos.add(photo);
	    });
	    return photos;
	}

}
