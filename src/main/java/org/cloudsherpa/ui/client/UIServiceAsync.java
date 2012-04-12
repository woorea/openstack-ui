package org.cloudsherpa.ui.client;

import java.util.Collection;
import java.util.List;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.User;
import org.openstack.model.images.Image;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UIServiceAsync {

	void associateFloatingIp(String ip, String serverId,
			AsyncCallback<List<FloatingIp>> callback);

	void attachVolume(AsyncCallback<List<Volume>> callback);

	void createEndpoint(Endpoint endpoint,
			AsyncCallback<List<Endpoint>> callback);

	void createFlavor(Flavor flavor, AsyncCallback<List<Flavor>> callback);

	void createFloatingIp(AsyncCallback<List<FloatingIp>> callback);

	void createImage(Image image, AsyncCallback<List<Image>> callback);

	void createKeyPair(String name, AsyncCallback<KeyPair> callback);

	void createRole(Role role, AsyncCallback<List<Role>> callback);

	void createSecurityGroup(SecurityGroupForCreate securityGroup,
			AsyncCallback<List<SecurityGroup>> callback);

	void createSecurityGroupRule(SecurityGroupRuleForCreate rule,
			AsyncCallback<SecurityGroup> callback);

	void createServer(ServerForCreate serverForCreate,
			AsyncCallback<List<Server>> callback);

	void createService(Service service, AsyncCallback<List<Service>> callback);

	void createSnapshots(SnapshotForCreate snapshotForCreate,
			AsyncCallback<List<Snapshot>> callback);

	void createTenant(Tenant tenant, AsyncCallback<List<Tenant>> callback);

	void createUser(User user, AsyncCallback<List<User>> callback);

	void createVolume(VolumeForCreate volumeForCreate,
			AsyncCallback<List<Volume>> callback);

	void deleteEndpoint(Collection<String> ids,
			AsyncCallback<List<Endpoint>> callback);

	void deleteFlavors(Collection<String> ids,
			AsyncCallback<List<Flavor>> callback);

	void deleteFloatingIps(Collection<Integer> ids,
			AsyncCallback<List<FloatingIp>> callback);

	void deleteImages(Collection<String> ids,
			AsyncCallback<List<Image>> callback);

	void deleteKeyPairs(Collection<String> names,
			AsyncCallback<List<KeyPair>> callback);

	void deleteRoles(Collection<String> ids, AsyncCallback<List<Role>> callback);

	void deleteSecurityGroupRule(Collection<Integer> ids,
			AsyncCallback<SecurityGroup> callback);

	void deleteSecurityGroups(Collection<Integer> ids,
			AsyncCallback<List<SecurityGroup>> callback);

	void deleteServers(Collection<String> ids,
			AsyncCallback<List<Server>> callback);

	void deleteSnapshots(Collection<Integer> ids,
			AsyncCallback<List<Snapshot>> callback);

	void deleteTenants(Collection<String> ids,
			AsyncCallback<List<Tenant>> callback);

	void deleteUser(Collection<String> ids, AsyncCallback<List<User>> callback);

	void deleteVolumes(Collection<Integer> ids,
			AsyncCallback<List<Volume>> callback);

	void delteService(Collection<String> ids,
			AsyncCallback<List<Service>> callback);

	void detachVolumes(Integer id, String serverId,
			AsyncCallback<List<Volume>> callback);

	void disassociateFloatingIp(String ip,
			AsyncCallback<List<FloatingIp>> callback);

	void executeServerAction(Collection<String> id, ServerAction action,
			AsyncCallback<Server> callback);

	void listEndpoints(AsyncCallback<List<Endpoint>> callback);

	void listFlavors(int start, int max, AsyncCallback<List<Flavor>> callback);

	void listFloatingIps(AsyncCallback<List<FloatingIp>> callback);

	void listImages(int starts, int max, AsyncCallback<List<Image>> callback);

	void listKeyPairs(AsyncCallback<List<KeyPair>> callback);

	void listRoles(AsyncCallback<List<Role>> callback);

	void listSecurityGroups(AsyncCallback<List<SecurityGroup>> callback);

	void listServers(int start, int max, AsyncCallback<List<Server>> callback);

	void listServices(AsyncCallback<List<Service>> callback);

	void listSnapshots(AsyncCallback<List<Snapshot>> callback);

	void listTenants(int start, int max, AsyncCallback<List<Tenant>> callback);

	void listUsers(AsyncCallback<List<User>> callback);

	void listVolumes(AsyncCallback<List<Volume>> callback);

}
