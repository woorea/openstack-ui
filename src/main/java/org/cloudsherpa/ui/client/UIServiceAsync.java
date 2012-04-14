package org.cloudsherpa.ui.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRule;
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
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.images.Image;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UIServiceAsync {

	void associateFloatingIp(String ip, String serverId,
			AsyncCallback<FloatingIp> callback);

	void attachVolume(Integer id, String serverId,
			AsyncCallback<Volume> callback);

	void createEndpoint(Endpoint endpoint, AsyncCallback<Endpoint> callback);

	void createFlavor(Flavor flavor, AsyncCallback<Flavor> callback);

	void createFloatingIp(AsyncCallback<FloatingIp> callback);

	void createImage(Image image, AsyncCallback<Image> callback);

	void createKeyPair(String name, AsyncCallback<KeyPair> callback);

	void createRole(Role role, AsyncCallback<Role> callback);

	void createSecurityGroup(SecurityGroupForCreate securityGroup,
			AsyncCallback<SecurityGroup> callback);

	void createSecurityGroupRule(SecurityGroupRuleForCreate rule,
			AsyncCallback<SecurityGroupRule> callback);

	void createServer(ServerForCreate serverForCreate,
			AsyncCallback<Server> callback);

	void createService(Service service, AsyncCallback<Service> callback);

	void createSnapshots(SnapshotForCreate snapshotForCreate,
			AsyncCallback<Snapshot> callback);

	void createTenant(Tenant tenant, AsyncCallback<Tenant> callback);

	void createUser(UserForCreate userForCreate, AsyncCallback<User> callback);

	void createVolume(VolumeForCreate volumeForCreate,
			AsyncCallback<Volume> callback);

	void deleteEndpoint(String id, AsyncCallback<Void> callback);

	void deleteEndpoints(String[] ids, AsyncCallback<Void> callback);

	void deleteFlavor(String id, AsyncCallback<Void> callback);

	void deleteFlavors(String[] ids, AsyncCallback<Void> callback);

	void deleteFloatingIp(Integer id, AsyncCallback<Void> callback);

	void deleteFloatingIps(Integer[] ids, AsyncCallback<Void> callback);

	void deleteImage(String id, AsyncCallback<Void> callback);

	void deleteImages(String[] id, AsyncCallback<Void> callback);

	void deleteKeyPair(String name, AsyncCallback<Void> callback);

	void deleteKeyPairs(String[] names, AsyncCallback<Void> callback);

	void deleteRole(String id, AsyncCallback<Void> callback);

	void deleteRoles(String[] ids, AsyncCallback<Void> callback);

	void deleteSecurityGroup(Integer id, AsyncCallback<Void> callback);

	void deleteSecurityGroupRule(Integer id, AsyncCallback<Void> callback);

	void deleteSecurityGroupRules(Integer[] ids, AsyncCallback<Void> callback);

	void deleteSecurityGroups(Integer[] ids, AsyncCallback<Void> callback);

	void deleteServer(String id, AsyncCallback<Void> callback);

	void deleteServers(String[] ids, AsyncCallback<Void> callback);

	void deleteService(String id, AsyncCallback<Void> callback);

	void deleteServices(String[] ids, AsyncCallback<Void> callback);

	void deleteSnapshot(Integer ids, AsyncCallback<Void> callback);

	void deleteSnapshots(Integer[] ids, AsyncCallback<Void> callback);

	void deleteTenant(String id, AsyncCallback<Void> callback);

	void deleteTenants(String[] ids, AsyncCallback<Void> callback);

	void deleteUser(String id, AsyncCallback<Void> callback);

	void deleteUsers(String[] ids, AsyncCallback<Void> callback);

	void deleteVolume(Integer id, AsyncCallback<Void> callback);

	void deleteVolumes(Integer[] id, AsyncCallback<Void> callback);

	void detachVolume(Integer id, AsyncCallback<Volume> callback);

	void disassociateFloatingIp(String ip, AsyncCallback<FloatingIp> callback);

	void executeServerAction(Collection<String> id, ServerAction action,
			AsyncCallback<Serializable> callback);

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
