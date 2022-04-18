import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './client.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClientDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">
          <Translate contentKey="fotografcimApp.client.detail.title">Client</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="clientName">
              <Translate contentKey="fotografcimApp.client.clientName">Client Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientName}</dd>
          <dt>
            <span id="clientSurname">
              <Translate contentKey="fotografcimApp.client.clientSurname">Client Surname</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientSurname}</dd>
          <dt>
            <span id="clientMail">
              <Translate contentKey="fotografcimApp.client.clientMail">Client Mail</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientMail}</dd>
          <dt>
            <span id="clientPhone">
              <Translate contentKey="fotografcimApp.client.clientPhone">Client Phone</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientPhone}</dd>
          <dt>
            <span id="clientAddress">
              <Translate contentKey="fotografcimApp.client.clientAddress">Client Address</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientAddress}</dd>
          <dt>
            <Translate contentKey="fotografcimApp.client.company">Company</Translate>
          </dt>
          <dd>{clientEntity.company ? clientEntity.company.companyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
