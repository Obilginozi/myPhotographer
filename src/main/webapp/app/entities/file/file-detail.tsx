import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './file.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FileDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fileEntity = useAppSelector(state => state.file.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fileDetailsHeading">
          <Translate contentKey="fotografcimApp.file.detail.title">File</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fotografcimApp.file.id">Id</Translate>
            </span>
          </dt>
          <dd>{fileEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="fotografcimApp.file.name">Name</Translate>
            </span>
          </dt>
          <dd>{fileEntity.name}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="fotografcimApp.file.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {fileEntity.photo ? (
              <div>
                {fileEntity.photoContentType ? (
                  <a onClick={openFile(fileEntity.photoContentType, fileEntity.photo)}>
                    <img src={`data:${fileEntity.photoContentType};base64,${fileEntity.photo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {fileEntity.photoContentType}, {byteSize(fileEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="fotografcimApp.file.album">Album</Translate>
          </dt>
          <dd>{fileEntity.album ? fileEntity.album.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file/${fileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FileDetail;
