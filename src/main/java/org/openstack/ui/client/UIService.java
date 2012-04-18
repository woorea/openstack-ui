package org.openstack.ui.client;


import java.io.Serializable;
import java.util.List;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FlavorList;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRule;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.ServerList;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.TenantList;
import org.openstack.model.identity.User;
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.images.Image;
import org.openstack.model.images.ImageList;
import org.openstack.ui.client.common.UIException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("../ui/service")
public interface UIService extends RemoteService {
	
	public void authenticate(String tenantId) throws UIException;
	
	public TenantList listUserTenants() throws UIException;
	
	public ServerList listServers(int start, int max) throws UIException;
	
	public Server create(ServerForCreate serverForCreate) throws UIException;
	
	public void deleteServer(String id) throws UIException;
	
	public void deleteServers(String[] ids) throws UIException;
	
	public Serializable executeServerAction(String id, ServerAction action) throws UIException;
	
	public FlavorList listFlavors(int start, int max) throws UIException;
	
	public Flavor create(Flavor flavor) throws UIException;
	
	public void deleteFlavor(String id) throws UIException;
	
	public void deleteFlavors(String[] ids) throws UIException;
	
	public ImageList listImages(int starts, int max) throws UIException;
	
	public Image create(Image image) throws UIException;
	
	public void deleteImage(String id) throws UIException;
	
	public void deleteImages(String[] id) throws UIException;
	
	public List<FloatingIp> listFloatingIps() throws UIException;
	
	public FloatingIp createFloatingIp() throws UIException;
	
	public void deleteFloatingIp(Integer id) throws UIException;
	
	public void deleteFloatingIps(Integer[] ids) throws UIException;
	
	public List<Volume> listVolumes() throws UIException;
	
	public Volume create(VolumeForCreate volumeForCreate) throws UIException;
	
	public void deleteVolume(Integer id) throws UIException;
	
	public void deleteVolumes(Integer[] id) throws UIException;
	
	public Volume attachVolume(Integer id, String serverId) throws UIException;
	
	public Volume detachVolume(Integer id) throws UIException;
	
	public List<Snapshot> listSnapshots() throws UIException;
	
	public Snapshot create(SnapshotForCreate snapshotForCreate) throws UIException;
	
	public void deleteSnapshot(Integer ids) throws UIException;
	
	public void deleteSnapshots(Integer[] ids) throws UIException;
	
	public List<KeyPair> listKeyPairs() throws UIException;
	
	public KeyPair createKeyPair(String name) throws UIException;
	
	public void deleteKeyPair(String name) throws UIException;

	public void deleteKeyPairs(String[] names) throws UIException;
	
	public List<SecurityGroup> listSecurityGroups() throws UIException;
	
	public SecurityGroup create(SecurityGroupForCreate securityGroup) throws UIException;
	
	public SecurityGroup showSecurityGroup(Integer id) throws UIException;
	
	public void deleteSecurityGroup(Integer id) throws UIException;
	
	public void deleteSecurityGroups(Integer[] ids) throws UIException;
	
	public SecurityGroupRule create(SecurityGroupRuleForCreate rule) throws UIException;
	
	public void deleteSecurityGroupRule(Integer id) throws UIException;
	
	public void deleteSecurityGroupRules(Integer[] ids) throws UIException;
	
	//
	
	public TenantList listTenants(int start, int max) throws UIException;
	
	public Tenant create(Tenant tenant) throws UIException;
	
	public void deleteTenant(String id) throws UIException;
	
	public void deleteTenants(String[] ids) throws UIException;
	
	public List<User> listUsers() throws UIException;
	
	public User create(UserForCreate userForCreate) throws UIException;
	
	public void deleteUser(String id) throws UIException;
	
	public void deleteUsers(String[] ids) throws UIException;
	
	public List<Role> listRoles() throws UIException;
	
	public Role create(Role role) throws UIException;
	
	public void deleteRole(String id) throws UIException;
	
	public void deleteRoles(String[] ids) throws UIException;
	
	public List<Service> listServices() throws UIException;
	
	public Service create(Service service) throws UIException;
	
	public void deleteService(String id) throws UIException;
	
	public void deleteServices(String[] ids) throws UIException;
	
	public List<Endpoint> listEndpoints() throws UIException;
	
	public Endpoint create(Endpoint endpoint) throws UIException;
	
	public void deleteEndpoint(String id) throws UIException;
	
	public void deleteEndpoints(String[] ids) throws UIException;
}
