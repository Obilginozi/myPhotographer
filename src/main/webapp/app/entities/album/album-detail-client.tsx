import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat, getUrlParameter } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getEntity } from './album.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { login } from 'app/shared/reducers/authentication';
import { IAlbum } from 'app/shared/model/album.model';

export const AlbumDetailClient = (props: RouteComponentProps<{ token: string }>) => {
  const dispatch = useAppDispatch();
  const [images, setImages] = useState<any>();

  const [test, setTest] = useState<IAlbum>({
    id: '',
    token: '',
    createDate: '',
    files: [{ id: '', name: '', photo: '', photoContentType: '' }],
    client: null,
  });

  useEffect(() => {
    if (localStorage.getItem('ACCESS_TOKEN') == null) {
      login('admin', 'admin', false);
    }
    dispatch(getEntity(props.match.params.token));
  }, []);
  const albumEntity = useAppSelector(state => state.album.entity);

  useEffect(() => {
    setTest(albumEntity);
  }, [albumEntity]);

  useEffect(() => {
    const tempImages = test.id
      ? test.files.map(file => {
          return <img key={file.id} src={'data:image/png;base64,' + file.photo} style={{ alignItems: 'flex-start', margin: '40px' }} />;
        })
      : null;
    setImages(tempImages);
  }, [test]);

  /* eslint-disable no-console */
  console.log(albumEntity.files);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="albumDetailsHeading">
          <Translate contentKey="fotografcimApp.album.detail.title">Album</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fotografcimApp.album.id">Id</Translate>
            </span>
          </dt>
          <dd>{albumEntity.id}</dd>
          <dt>
            <span id="token">
              <Translate contentKey="fotografcimApp.album.token">Token</Translate>
            </span>
          </dt>
          <dd>{albumEntity.token}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="fotografcimApp.album.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {albumEntity.createDate ? <TextFormat value={albumEntity.createDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="fotografcimApp.album.client">Client</Translate>
          </dt>
          <dd>{albumEntity.client ? albumEntity.client.id : ''}</dd>
          <dd>{images}</dd>
        </dl>
      </Col>
    </Row>
  );
};

export default AlbumDetailClient;
